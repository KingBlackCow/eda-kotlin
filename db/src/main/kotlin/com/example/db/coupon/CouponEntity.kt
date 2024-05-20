package com.example.db.coupon

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "coupon")
class CouponEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val userId: Long? = null,

    @Column(name = "coupon_event_id")
    var couponEventId: Long? = null,
    val issuedAt: LocalDateTime? = null,
    val usedAt: LocalDateTime? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_event_id", nullable = false, insertable = false, updatable = false)
    val couponEvent: CouponEventEntity? = null
) {

}
