package com.aria.server.art.domain

import javax.persistence.Embeddable

@Embeddable
class Size (
    width: Int,
    height: Int
) {
    var width: Int = width
        protected set

    var height: Int = height
        protected set
}
