(ns play-clj-ground.core.desktop-launcher
  (:require [play-clj-ground.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. play-clj-ground "play-clj-ground" 800 600)
  (Keyboard/enableRepeatEvents true))
