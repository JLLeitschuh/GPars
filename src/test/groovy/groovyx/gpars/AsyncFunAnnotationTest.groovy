// GPars - Groovy Parallel Systems
//
// Copyright © 2008--2011  The original author or authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package groovyx.gpars

import groovyx.gpars.dataflow.Promise
import spock.lang.Specification

class AsyncFunAnnotationTest extends Specification {

    def "test basic annotation usage"() {

        when:
        boolean wasCalled = false
        groovyx.gpars.GParsPool.withPool(5) {
            def tester = new TestSum()
            assert tester.sum(1, 2).val == 3
            assert tester.sum(10, 15) instanceof Promise
            wasCalled = true
        }

        then:
        wasCalled
    }

    def "test annotation on already annotated field value"() {

        when:
        boolean wasCalled = false
        groovyx.gpars.GParsPool.withPool(5) {
            def tester = new TestSum()
            assert tester.sum2(1, 2).val == 3
            assert tester.sum2(10, 15) instanceof Promise
            wasCalled = true
        }

        then:
        wasCalled
    }

    def "test annotation on method expression"() {

        when:
        boolean wasCalled = false
        groovyx.gpars.GParsPool.withPool(5) {
            def tester = new TestSum()
            assert tester.sum3(1, 2).val == 3
            assert tester.sum3(10, 15) instanceof Promise
            wasCalled = true
        }

        then:
        wasCalled
    }


    def "test annotation with property accessors"() {

        when:
        boolean wasCalled = false
        groovyx.gpars.GParsPool.withPool(5) {
            def tester = new TestSum()
            assert tester.sum4(1, 2).val == 3
            assert tester.sum4(10, 15) instanceof Promise
            wasCalled = true
        }

        then:
        wasCalled
    }

    def "test annotation parameters"() {

        when:
        boolean wasCalled = false
        groovyx.gpars.GParsPool.withPool(5) {
            def tester = new TestSum()
            assert tester.sum5(1, 2).val == 3
            assert tester.sum5(10, 15) instanceof Promise
            wasCalled = true
        }

        then:
        wasCalled
    }

    def "test annotation parameters with older thread pool"() {

        when:
        boolean wasCalled = false
        groovyx.gpars.GParsExecutorsPool.withPool(5) {
            def tester = new MyOtherTester()
            assert tester.sum6(1, 2).val == 3
            assert tester.sum6(10, 15) instanceof Promise
            wasCalled = true
        }

        then:
        wasCalled
    }

    class TestSum {
        @AsyncFun
        def sum = {a, b -> a + b }

        @AsyncFun
        def sum2 = getClosureSum()

        @AsyncFun
        def sum3 = getNewClosureSum()

        def holder = [a: [b: [c: getNewClosureSum()]]]

        @AsyncFun
        def sum4 = holder.a.b.c

        @AsyncFun(GParsPoolUtil)
        def sum5 = getNewClosureSum()

        def getClosureSum() {
            sum
        }

        def getNewClosureSum() {
            { a, b -> a + b }
        }
    }

    class MyOtherTester {

        @AsyncFun(GParsExecutorsPoolUtil)
        def sum6 = {a, b -> a + b }

    }
}
