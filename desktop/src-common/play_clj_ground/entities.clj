(ns play-clj-ground.entities
  (:require [play-clj.entities :refer [Entity]]))

(defprotocol MapP
  (get-viewport [this] "returns the viewport associated with the map")
  (get-cell [this coords] "return the cell at given coordinates")
  (get-objects-at [this coords] "return the objects at given coordinates"))

(defprotocol ViewportP
  (get-coords [this] "get coordinates of viewport")
  (point-at [this coords] "center the viewport at given coordinates"))

(defprotocol CellP
  (get-texture [this] "returns texture for this cell")
  (get-objects [this] "returns objects residing at this cell")
  (set-texture [this new-texture] "sets the texture for this cell")
  (modify-objects [this fn] "modifies objects in this cell using fn"))

(deftype TiledMap [cells viewport]
  MapP
  (get-viewport [this] viewport)
  (get-cell [this [x y]] nil)
  (get-objects-at [this [x y]] nil))

(deftype Viewport [^:volatile-mutable coords tiled-map]
  ViewportP
  (get-coords [this] coords)
  (point-at [this new-coords] (set! coords new-coords))
  Entity
  (draw-entity! [this screen batch]))
