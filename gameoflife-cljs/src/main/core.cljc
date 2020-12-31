(ns core)

; Any live cell with two or three live neighbours survives.
; Any dead cell with three live neighbours becomes a live cell.
; All other live cells die in the next generation. Similarly, all other dead cells stay dead.


(def large-grid
  {
   '(1 6) false '(2 6) false '(3 6) false '(4 6) false '(5 6) false '(6 6) false
   '(1 5) false '(2 5) false '(3 5) false '(4 5) false '(5 5) false '(6 5) false
   '(1 4) false '(2 4) true '(3 4) true '(4 4) true '(5 4) false '(6 4) false
   '(1 3) false '(2 3) false '(3 3) true '(4 3) true '(5 3) false '(6 3) false
   '(1 2) false '(2 2) false '(3 2) true '(4 2) false '(5 2) false '(6 2) false
   '(1 1) false '(2 1) false '(3 1) false '(4 1) false '(5 1) false '(6 1) false
   }
  )

(defn is-alive? [grid xy]
  (get grid xy))

(defn cell-list [grid [x y]]
  (map (partial is-alive? grid)
    [(list (- x 1) (- y 1)) (list x (- y 1)) (list (+ x 1) (- y 1))
     (list (- x 1) y) (list (+ x 1) y)
     (list (- x 1) (+ y 1)) (list x (+ y 1)) (list (+ x 1) (+ y 1))]))


(defn my-add [acc alive?]
  (if alive?
    (inc acc)
    acc))

(defn gol [grid acc xy alive?]
  (let [alive-count (reduce my-add 0 (cell-list grid xy))
        new-state
        (or
          (and (not alive?) (= alive-count 3))
          (and alive? (or (= alive-count 2) (= alive-count 3))))]

    (assoc acc xy new-state)))

(defn main-loop [grid]
  (js/setTimeout
    (partial main-loop
      (reduce-kv (partial gol grid) {} grid))
    2000)
  )

(defn -main []
  (println "Hello, World!")
  (main-loop large-grid))

