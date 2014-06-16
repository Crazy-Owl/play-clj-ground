(ns play-clj-ground.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]
            [play-clj.g2d :refer :all]))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (label "Hello world!" (color :white)))

  :on-render
  (fn [screen entities]
    (clear!)
    (render! screen entities))

  :my-test-fn
  (fn [screen entities]
    (println (str "in my-test-fn, arg is " (:arg screen)))))

(defn update-labels ; update labels with frames-per-second and delta-time data
  [entities]
  (map (fn [entity]
         (case (:id entity)
           :fps (doto entity (label! :set-text (str (graphics! :get-frames-per-second))))
           :dt (doto entity (label! :set-text (str (graphics! :get-delta-time))))
           :else entity))
       entities))

(defscreen overlay-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    [(assoc (label "0" (color :green)) :x 100 :y 200 :id :fps)
     (assoc (label "0" (color :red)) :x 100 :y 250 :id :dt)])

  :on-render
  (fn [screen entities]
    (->> entities
         update-labels
         (render! screen)))

  :on-key-down
  (fn [screen entities]
    (run! main-screen :my-test-fn :arg (:key screen))
    entities))

(defgame play-clj-ground
  :on-create
  (fn [this]
    (set-screen! this main-screen overlay-screen)))
