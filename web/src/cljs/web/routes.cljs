(ns web.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [secretary.core :as secretary]
            [goog.events :as gevents]
            [goog.history.EventType :as EventType]
            [re-frame.core :as rf]
            ; [web.events :as events]
            ))

(defn hook-browser-navigation! []
  (doto (History.)
    (gevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")
  ;; --------------------
  ;; define routes here
  (defroute "/" []
    (rf/dispatch [:set-active-panel :home-panel]))

  (defroute "/about" []
    (rf/dispatch [:set-active-panel :about-panel]))

  (defroute "/experience" []
    (rf/dispatch [:set-active-panel :experience-panel]))

  (defroute "/contact" []
    (rf/dispatch [:set-active-panel :contact-panel]))


  ;; --------------------
  (hook-browser-navigation!))
