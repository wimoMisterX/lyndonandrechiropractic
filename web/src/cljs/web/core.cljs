(ns web.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [web.events]
            [web.subs]
            [web.routes :as routes]
            [web.views :as views]
            [web.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (rf/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
