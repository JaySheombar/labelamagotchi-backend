package com.labela.labelamagotchi.labelamagotchi.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {

    private val mockBankDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`() {
        // when
        val banks = mockBankDataSource.retrieveBanks()
        
        // then
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        // when
        val banks = mockBankDataSource.retrieveBanks()

        // then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank() }
        assertThat(banks).anyMatch { it.trust != 0.0 }
        assertThat(banks).anyMatch { it.transactionFee != 0 }
    }

    @Test
    fun `should be unique account numbers`() {
        // when
        val banks = mockBankDataSource.retrieveBanks()

        val numberOfIdenticalBanks = banks
            .groupingBy { it.accountNumber }
            .eachCount()
            .filter { (_, value) -> value >= 2 }
            .size

        // then
        assertThat(numberOfIdenticalBanks).isLessThan(1)
    }
}