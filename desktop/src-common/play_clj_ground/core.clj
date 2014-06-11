(ns play-clj-ground.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (label "Hello world!" (color :white)))

  :on-render
  (fn [screen entities]
    (println entities)
    (clear!)
    (render! screen entities)))

(defscreen overlay-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    (assoc (label "WWWWW" (color :green)) :x 100 :y 200 :pppp true))

  :on-render
  (fn [screen entities]
    (println entities)
    (render! screen entities)))

(defgame play-clj-ground
  :on-create
  (fn [this]
    (set-screen! this main-screen overlay-screen)))
