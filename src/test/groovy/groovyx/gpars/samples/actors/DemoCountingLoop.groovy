// GPars - Groovy Parallel Systems
//
// Copyright © 2008-10  The original author or authors
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package groovyx.gpars.samples.actors

import groovyx.gpars.actor.Actor
import static groovyx.gpars.actor.Actors.actor

/**
 * Demonstrates the use of conditional actor loops
 *
 * @author Vaclav Pech
 * Date: Aug 25th 2010
 */

final Actor actor = actor {
    def candidates = []
    loop(3) {
        react {
            candidates << it
            if (candidates.size() == 3) println "The best offer is ${candidates.max()}"
        }
    }
}

actor 10
actor 30
actor 20
actor.join()
