(ns repl-helper
  (:require [clojure.reflect :as reflect]
            [clojure.string :as string]))

(defn- ls-ns
  [sym]
  (let [ms (map meta (vals (ns-publics sym)))
        ms (sort-by :name ms)]
    (doseq [m ms]
      (println
       (str (:name m)
            (if (:arglists m) (pr-str (:arglists m)) ""))))))

(defn- ls-var
  [sym]
  (let [m (meta (resolve sym))]
    (when-let [doc (:doc m)]
      (println doc))))

(defn- ls-class
  [cls]
  (let [ms (filter #(:public (:flags %))
                   (:members (reflect/type-reflect cls)))
        ms (sort-by :name ms)]
    (doseq [m ms]
      (println
       (str (if (:static (:flags m)) "[s] " "    ")
            (:name m)
            "(" (string/join ", " (:parameter-types m)) ")"
            (when (:return-type m)
              (str " -> " (:return-type m))))))))

(defn- ls-literal
  [literal]
  (ls-class (class literal)))

(defn ls-
  ([]
   (doseq [ns (sort (map ns-name (all-ns)))]
     (println ns)))
  ([sym]
   (if (symbol? sym)
     (cond
       (find-ns sym)
       (ls-ns sym)
       (var? (resolve sym))
       (ls-var sym)
       (class? (resolve sym))
       (ls-class (resolve sym)))
     (ls-literal sym))))

(defmacro ls
  "list namespaces, members of namespace/class, doc of var."
  ([]
   `(ls-))
  ([sym]
   `(ls- '~sym)))

(defmacro dbg
  [form]
  `(let [form# ~form]
     (println '~form "=>" form#)))

