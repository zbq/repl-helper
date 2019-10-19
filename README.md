# repl-helper

A Clojure library designed to enhance REPL.

## Usage

### Add following code to ~/.lein/profiles.clj
    {:repl
        {:dependencies [[repl-helper "0.1.0"]]}}

### run lein repl
    (use 'repl-helper)

    (ls) -> print members of current namespace
    (ls :allns) -> print all namespaces
    (ls sym) -> print members of namespace (if sym resolve to a namespace)
             -> print members of class (if sym resolve to a class)
             -> print var doc (if sym resolve to a var)
             -> print members of sym's class (if sym is a literal)
    (ls ns-or-class pattern) -> print members of namespace or class
        (find pattern in member name, pattern could be a symbol/string/re-pattern)


    :examples
    (ls)
    ********** #namespace[repl-helper] **********
    dbg([form])
    ls([] [sym] [ns-or-class pattern])
    ls-([] [sym] [ns-or-class pattern])

    (ls :allns)
    cider.nrepl
    cider.nrepl.inlined-deps.cljs-tooling.v0v3v1.cljs-tooling.complete
    cider.nrepl.inlined-deps.cljs-tooling.v0v3v1.cljs-tooling.info
    ...

    (ls clojure.string)
    ********** #namespace[clojure.string] **********
    blank?([s])
    capitalize([s])
    ends-with?([s substr])
    escape([s cmap])
    ...
    
    (ls get)  =>
    ********** #'clojure.core/get **********
    get([map key] [map key not-found])
    Returns the value mapped to key, not-found or nil if key not present.

    (ls Long) or (ls 1) =>
    ********** java.lang.Long **********
    [s] BYTES()
    [s] MAX_VALUE()
    [s] MIN_VALUE()
    [s] SIZE()
    [s] TYPE()
    ...

    (ls Long value)
    ********** java.lang.Long **********
    [s] valueOf(long) -> java.lang.Long
    [s] valueOf(java.lang.String) -> java.lang.Long
    [s] valueOf(java.lang.String, int) -> java.lang.Long

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
