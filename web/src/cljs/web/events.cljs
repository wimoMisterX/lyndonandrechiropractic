(ns web.events
  (:require [re-frame.core :as re-frame]
            [web.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
 ::contact-form-value-change
 (fn [db [_ field value]]
   (assoc-in db [:contact-form field] value)))
