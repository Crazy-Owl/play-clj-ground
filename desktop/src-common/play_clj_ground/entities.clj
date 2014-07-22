(ns play-clj-ground.entities
  (:require [play-clj.entities :refer [Entity]]))

(defprotocol MapP
  (get-viewport [this] "returns the viewport associated with the map")
  (get-cell [this coords] "return the cell at given coordinates"))

(defprotocol ViewportP
  (get-coords [this] "get coordinates of viewport")
  (point-at [this coords] "center the viewport at given coordinates"))

(deftype TiledMap [cells viewport]
  MapP
  (get-viewport [this] viewport)
  (get-cell [this [x y]] nil))

(deftype Viewport [^:volatile-mutable coords tiled-map]
  ViewportP
  (get-coords [this] coords)
  (point-at [this new-coords] (set! coords new-coords))
  Entity
  (draw-entity! [this screen batch]))
