(ns play-clj-ground.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]
            [play-clj.g2d :refer :all]))

(def current-mode (atom nil))

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
    (println (str "in my-test-fn, arg is " (:arg screen) ", arg2 is " (:arg2 screen))))

  :on-touch-down
  (fn [screen entities]
    (println (str "current mode is" @current-mode))
    (when (= @current-mode :a)
      (let [{:keys [x y]} (input->screen screen (:input-x screen) (:input-y screen))]
        (conj entities (assoc (shape :filled
                                     :set-color (color :green)
                                     :rect 0 0 10 10)
                         :x (- x 5)
                         :y (- y 5)))))))

(defn update-labels ; update labels with frames-per-second and delta-time data
  [entities]
  (map (fn [entity]
         (condp = (:id entity)
           :fps (doto entity (label! :set-text (str (graphics! :get-frames-per-second))))
           :dt (doto entity (label! :set-text (str (graphics! :get-delta-time))))
           entity))
       entities))

(defscreen overlay-screen
  :on-show
  (fn [screen entities]
    (update! screen
             :renderer (stage)
             :test-message "test")
    [(assoc (label "0" (color :green)) :x 100 :y 200 :id :fps)
     (assoc (label "0" (color :red)) :x 100 :y 250 :id :dt)])

  :on-render
  (fn [screen entities]
    (->> entities
         update-labels
         (render! screen)))

  :on-key-down
  (fn [screen entities]
    (run! main-screen :my-test-fn :arg (:key screen) :arg2 (:test-message screen))
    (case @current-mode
      :begin (condp = (:key screen)
               (key-code :a) (reset! current-mode :a)
               true)
      :a (condp = (:key screen)
           (key-code :a) (reset! current-mode :begin)
           true))
    entities))

(defgame play-clj-ground
  :on-create
  (fn [this]
    (reset! current-mode :begin)
    (set-screen! this main-screen overlay-screen)))
