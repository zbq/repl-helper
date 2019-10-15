# repl-helper

A Clojure library designed to improve REPL.

## Usage

### Add ~/.lein/profiles.clj
    {:repl
        {:dependencies [[repl-helper "0.1.0"]]}}

### run lein repl
    (use 'repl-helper)
    
    (ls)                  => list all namespaces
    (ls clojure.core)     => list members of clojure.core namespace
    (ls Integer)          => list members of java.lang.Integer class
    (ls 1)                => list members of java.lang.Long class
    (ls "")               => list members of java.lang.String class
    (ls list)             => list documentation of clojure.core/list

    (dbg (+ 1 1))         => (+ 1 1) => 2
## License

Copyright © 2019 zbq

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
