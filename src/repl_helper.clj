(ns repl-helper
  (:require [clojure.reflect :as reflect]
            [clojure.string :as string]))

(defn- ls-ns
  [ns & [pattern]]
  (println "**********" ns "**********")
  (let [ms (map meta (vals (ns-publics ns)))
        ms (if pattern
             (filter #(re-find pattern (str (:name %))) ms)
             ms)
        ms (sort-by :name ms)]
    (doseq [m ms]
      (println
       (str (:name m)
            (when-let [args (:arglists m)]
              (pr-str args)))))))

(defn- ls-var
  [sym]
  (println "**********" sym "**********")
  (let [m (meta sym)]
    (when-let [args (:arglists m)]
      (println (str (:name m) args)))
    (when-let [doc (:doc m)]
      (println doc))))

(defn- ls-class
  [cls & [pattern]]
  (println "**********" cls "**********")
  (let [ms (filter #(:public (:flags %))
                   (:members (reflect/type-reflect cls)))
        ms (if pattern
             (filter #(re-find pattern (str (:name %))) ms)
             ms)
        ms (sort-by :name ms)]
    (doseq [m ms]
      (println
       (str (if (:static (:flags m)) "[s] " "    ")
            (:name m)
            "(" (string/join ", " (:parameter-types m)) ")"
            (when-let [rt (:return-type m)]
              (str " -> " rt)))))))

(defn ls-
  ([]
   (ls-ns *ns*))
  ([sym]
   (if (symbol? sym)
     (do
       (when-let [ns (find-ns sym)]
         (ls-ns ns))
       (when-let [ns ((ns-aliases *ns*) sym)]
         (ls-ns ns))
       (when (class? (resolve sym))
         (ls-class (resolve sym)))
       (when (var? (resolve sym))
         (ls-var (resolve sym))))
     (if (= sym :allns)
       (doseq [ns (sort (map ns-name (all-ns)))]
         (println ns))
       (ls-class (class sym)))))
  ([ns-or-class pattern]
   (let [pattern (re-pattern
                  (if (symbol? pattern)
                    (str pattern)
                    pattern))]
     (when-let [ns (find-ns ns-or-class)]
       (ls-ns ns pattern))
     (when-let [ns ((ns-aliases *ns*) ns-or-class)]
       (ls-ns ns pattern))
     (when (class? (resolve ns-or-class))
       (ls-class (resolve ns-or-class) pattern))))
  )
   
(defmacro ls
  "(ls) -> print members of current namespace
  (ls :allns) -> print all namespaces
  (ls sym) -> print members of namespace (if sym resolve to a namespace)
           -> print members of class (if sym resolve to a class)
           -> print var doc (if sym resolve to a var)
           -> print members of sym's class (if sym is a literal)
  (ls ns-or-class pattern) -> print members of namespace or class
      (find pattern in member name, pattern could be a symbol/string/re-pattern)"
  ([]
   `(ls-))
  ([sym]
   `(ls- '~sym))
  ([ns-or-class pattern]
   `(ls- '~ns-or-class '~pattern))
  )

(defmacro dbg
  [form]
  `(let [form# ~form]
     (println '~form "=>" form#)))

