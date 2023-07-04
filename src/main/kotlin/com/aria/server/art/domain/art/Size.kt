package com.aria.server.art.domain.art

import javax.persistence.Embeddable

@Embeddable
class Size (
    width: Float,
    height: Float
) {
    var width: Float = width
        protected set

    var height: Float = height
        protected set
}
