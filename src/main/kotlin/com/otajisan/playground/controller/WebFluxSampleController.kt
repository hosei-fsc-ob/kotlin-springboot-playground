package com.otajisan.playground.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

/**
 * WebFluxしてみよう
 */
@RestController
@RequestMapping
class WebFluxSampleController() {

    /**
     * Monoは「1つの値を返すかもしれない」という文脈
     * - RPCのつもりで書いているのでpathは気にしないで
     */
    @GetMapping("/webflux/get-by-mono")
    fun getByMono(): Mono<List<String>> {
        return Mono.just(MEMBERS)
                .map { list ->
                    list.map { member -> createIntroduction(member) }.toList()
                }
    }

    /**
     * Fluxは「n個の値を返す」という文脈
     * - RPCのつもりで書いているのでpathは気にしないで
     */
    @GetMapping("/webflux/get-by-flux")
    fun getByFlux(): Flux<String> {
        return Flux.fromIterable(MEMBERS)
                .map { createIntroduction(it) }
    }

    fun createIntroduction(member: Member): String {
        Thread.sleep(500L)
        return "第${member.folkAge}代の${member.name}です。"
    }

    companion object {
        val MEMBERS = listOf(Member("shuhei", 34),
                Member("taji", 37),
                Member("bunta", 36))
    }

    data class Member(val name: String, val folkAge: Int)

}
