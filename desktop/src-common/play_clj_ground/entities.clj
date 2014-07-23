(ns play-clj-ground.entities
  (:require [play-clj.entities :refer [Entity draw-entity!]]))

(defprotocol MapP
  (get-viewport [this] "returns the viewport associated with the map")
  (get-cell [this coords] "return the cell at given coordinates")
  (set-cell! [this coords new-cell] "set the given cell")
  (get-objects-at [this coords] "return the objects at given coordinates"))

(defprotocol ViewportP
  (get-coords [this] "get coordinates of viewport")
  (get-map [this] "get underlying map object")
  (point-at! [this coords] "center the viewport at given coordinates"))

(defprotocol CellP
  (get-texture [this] "returns texture for this cell")
  (get-objects [this] "returns objects residing at this cell")
  (set-texture! [this new-texture] "sets the texture for this cell")
  (modify-objects! [this f] "modifies objects in this cell using fn"))

(deftype MapCell [^:volatile-mutable texture params ^:volatile-mutable objects]
  CellP
  (get-texture [this] texture)
  (get-objects [this] objects)
  (set-texture! [this new-texture] (set! texture new-texture))
  (modify-objects! [this f] (set! objects (vec (doall (map f objects))))))

(deftype TiledMap [^:volatile-mutable cells viewport]
  MapP
  (get-viewport [this] viewport)
  (get-cell [this [x y]] nil)
  (get-objects-at [this [x y]] nil)
  (set-cell! [this [x y] new-cell]
    (let [cell-row (get cells y)]
      (set! cells (assoc cells y (assoc cell-row x new-cell)))))
  Entity
  (draw-entity! [this screen batch]
    (draw-entity! viewport screen batch)))

(deftype Viewport [^:volatile-mutable coords tiled-map]
  ViewportP
  (get-coords [this] coords)
  (get-map [this] tiled-map)
  (point-at! [this new-coords] (set! coords new-coords))
  Entity
  (draw-entity! [this screen batch]))
