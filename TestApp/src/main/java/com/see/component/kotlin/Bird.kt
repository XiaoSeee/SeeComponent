package com.see.component.kotlin

/**
 * @author by wuxiang@tinglibao.com.cn on 2020/11/20.
 */
class Bird : Flyer, Animal {
    override fun eat() {
        super<Animal>.eat()
    }
}