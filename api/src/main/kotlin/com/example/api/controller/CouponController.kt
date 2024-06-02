package com.example.api.controller

import com.example.api.controller.dto.CouponDto
import com.example.api.controller.dto.CouponIssueRequest
import com.example.api.domain.coupon.ResolvedCoupon
import com.example.api.service.CouponIssueHistoryService
import com.example.api.service.ListUsableCouponsService
import com.example.api.service.RequestCouponIssueService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.function.Function

@RestController
@RequestMapping("/coupons")
class CouponController(
    private val couponIssueHistoryService: CouponIssueHistoryService,
    private val requestCouponIssueService: RequestCouponIssueService,
    private val listUsableCouponsService: ListUsableCouponsService
) {
    @PostMapping
    fun issue(@RequestBody request: CouponIssueRequest): ResponseEntity<String> {
        if (!couponIssueHistoryService.isFirstRequestFromUser(request.couponEventId, request.userId)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already tried to issue a coupon\n")
        }

        if (!couponIssueHistoryService.hasRemainingCoupon(request.couponEventId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not enough available coupons\n")
        }

        requestCouponIssueService.queue(request.couponEventId, request.userId)
        return ResponseEntity.ok().body("Successfully Issued\n")
    }

    @GetMapping
    fun listUsableCoupons(
        @RequestParam(name = "userId", defaultValue = "0", required = false) userId: Long?
    ): ResponseEntity<List<CouponDto>> {
        val resolvedCoupons: List<ResolvedCoupon> = listUsableCouponsService.listByUserId(userId!!)
        return ResponseEntity.ok()
            .body(resolvedCoupons.stream().map { this.toDto(it) }.toList())
    }

    private fun toDto(resolvedCoupon: ResolvedCoupon): CouponDto {
        return CouponDto(
            resolvedCoupon.coupon.id!!,
            resolvedCoupon.couponEvent.displayName,
            resolvedCoupon.couponEvent.expiresAt
        )
    }
}