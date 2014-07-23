(ns play-clj-ground.textures)

(def default-storage (atom nil))

(defprotocol TextureStorageP
  (get-texture [this name] "get texture by its name. Loads textures on demand if needed")
  (load-textures [this spec] "load textures according to texture sheet specs"))

(deftype TextureStorage [path textures]
  TextureStorageP
  (get-texture [this name] (get textures name))
  (load-textures [this spec])) ;; TODO: spec format, load the specs

(defn load-texture [name & {:keys [storage]}]) ;; TODO: get current texture storage and load texture from it
