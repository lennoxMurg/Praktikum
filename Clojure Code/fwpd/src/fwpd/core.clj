(ns fwpd.core)
(def filename "suspects.csv")


(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter)))


;; Exercise 1 : Turn the result of your glitter filter into a list of names

(defn list-names
  [minimum-glitter records]
  (map :name (glitter-filter minimum-glitter records)))


;; Exercise 2 : Write a function, append, which will append a new suspect to your list of suspects

(defn append
  [vamp-map]
  (let [csv-line (str (:name vamp-map) "," (:glitter-index vamp-map) "\n")]
    (spit filename csv-line :append true)))
