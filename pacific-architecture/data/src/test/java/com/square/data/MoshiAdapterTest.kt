package com.square.data

import com.square.data.http.entities.ApiResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoshiAdapterTest {

    @Test
    fun nullToEmptyJsonAdapter_isCorrect() {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val adapter = moshi.adapter<Player>(
            Types.newParameterizedTypeWithOwner(
                MoshiAdapterTest::class.java,
                Player::class.java
            )
        )
        val toJson = adapter.toJson(
            Player(
                "Jake",
                "Wharton",
                true,
                24,
                200.00f,
                100.00,
                184.00,
                listOf(
                    Transaction("aaa", 100.00, 1),
                    Transaction("bbb", 100.00, 1),
                    Transaction("ccc", 100.00, 1)
                ),
                emptyList()
            )
        )
        println(toJson)
        val fromJson =
            """{"firstName":"a","lastName":"Wharton","isVip":true,"age":24,"amount":200.0,"weight":184,"height":184.0,"transactions1":[{"time":"aaa","amount":100.0},{"time":"bbb","amount":100.0},{"time":"ccc","amount":100.0}],"transactions2":[]}"""
        val kJson = Json(JsonConfiguration.Stable)
        kJson.parse(Player.serializer(), fromJson)

        val player = adapter.fromJson(fromJson)!!
        // assertTrue(player.firstName == "")
        // assertTrue(player.transactions2.isEmpty())
        println(player)

        val apiJson1 = """{"code":0,"msg":"null","data":{}}"""
        val apiJson2 = """{"code":0,"msg":"null","data":null}"""
        val apiJson3 = """{"code":0,"msg":null}"""
        val apiJson4 = """{"code":0}"""
        val apiJson5 = "{}"

        val adapter1 = moshi.adapter<ApiResponse<*>>(
            Types.newParameterizedType(
                ApiResponse::class.java,
                Transaction::class.java
            )
        )
        val a1 = adapter1.fromJson(apiJson1)
        val a2 = adapter1.fromJson(apiJson2)
        val a3 = adapter1.fromJson(apiJson3)
        val a4 = adapter1.fromJson(apiJson4)
        val a5 = adapter1.fromJson(apiJson5)
        println("Hello")
    }

    @Serializable
    data class Player(
        val firstName: String,
        val lastName: String,
        val isVip: Boolean,
        val age: Int,
        val amount: Float,
        val weight: Double,
        val height: Double,
        val transactions1: List<Transaction>,
        val transactions2: List<Transaction>
    )

    @Serializable
    data class Transaction(
        val time: String = "snw",
        val amount: Double = 1.00,
        val extra: Int = 1
    )
}

