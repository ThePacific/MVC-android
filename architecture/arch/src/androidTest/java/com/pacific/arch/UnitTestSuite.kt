package com.pacific.arch

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(RxTest::class, SystemIOTest::class)
class UnitTestSuite