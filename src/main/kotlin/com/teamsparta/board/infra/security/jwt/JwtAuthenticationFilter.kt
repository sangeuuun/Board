package com.teamsparta.board.infra.security.jwt

import com.teamsparta.board.infra.security.UserPrincipal
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtPlugin: JwtPlugin,
//    private val memberService: MemberServiceImpl
) : OncePerRequestFilter() {

    companion object {
        private val BEARER_PATTERN = Regex("^Bearer (.+?)$")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val jwt = request.getBearerToken()

        if (jwt != null) {
//            if (memberService.isTokenBlacklisted(jwt)) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is blacklisted")
//                return
//            }

            jwtPlugin.validateToken(jwt)
                .onSuccess {
                    val memberId = it.payload.subject.toLong()
                    val role = it.payload.get("role", String::class.java)
                    val nickname = it.payload.get("nickname", String::class.java)

                    val principal = UserPrincipal(
                        id = memberId,
                        nickname = nickname,
                        roles = setOf(role)
                    )
                    val authentication = JwtAuthenticationToken(
                        principal = principal,
                        details = WebAuthenticationDetailsSource().buildDetails(request)
                    )
                    SecurityContextHolder.getContext().authentication = authentication

                    request.setAttribute("accessToken", jwt)
                }
                .onFailure {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token")
                    return
                }
        }

        filterChain.doFilter(request, response)
    }

    private fun HttpServletRequest.getBearerToken(): String? {
        val headerValue = this.getHeader(HttpHeaders.AUTHORIZATION) ?: return null
        return BEARER_PATTERN.find(headerValue)?.groupValues?.get(1)
    }
}