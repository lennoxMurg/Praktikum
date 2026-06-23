
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
  (filter #(>= (:glitter-index %) minimum-glitter) records))


;; Exercise 1 : Turn the result of your glitter filter into a list of names

(defn list-names
  [minimum-glitter records]
  (map :name (glitter-filter minimum-glitter records)))


;; Exercise 2 : Write a function, append, which will append a new suspect to your list of suspects

(defn append
  [records name-str glitter-int]
  (let [new-suspect {:name name-str :glitter-index glitter-int}]
    (conj records new-suspect)))

;; Exercise 3 : Write a function, validate, which will check that :name and :glitter-index are present when you append

(def validation-rules {:name string? 
                       :glitter-index integer?})

(defn validate
  [validations record]
  (let [name-fn (:name validations)
        glitter-fn (:glitter-index validations)]
    (if (and (name-fn (:name record))
             (glitter-fn (:glitter-index record)))
      true
      false)))

;; The append function with the validate function check

(defn append-v2
  [records name-str glitter-int]
  (let [new-suspect {:name name-str :glitter-index glitter-int}]
    (if (validate validation-rules new-suspect)
      (do
        (println "Succes - Suspect added to list!")
        (conj records new-suspect))
      (do
        (println "Error - Name needs to be String and glitter-index needs to be int!")
        records)))) ;; Gibt einfach die alte Liste unverändert zurück

;; Exercise 4 : 

(defn record-to-csv
  [records]
  (let [lines (map (fn [r] (str (:name r) "," (:glitter-index r))) records)]
    (clojure.string/join "\n" lines)))
