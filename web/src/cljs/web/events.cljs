(ns web.events
  (:require [re-frame.core :as rf]
            [web.db :as db]
            [web.validators :refer [falsey? contact-form-validators]]))

(rf/reg-event-db :initialize-db
 (fn  [_ _]
   db/default-db))

(rf/reg-event-fx :set-active-panel
 (fn [cofx [_ active-panel]]
   (if (= (:contact-form-status (:db cofx)) :submitted)
     {:db (assoc (:db cofx) :active-panel active-panel)
      :dispatch [:reset-contact-form]}
     {:db (assoc (:db cofx) :active-panel active-panel)})))

(rf/reg-event-db :contact-form-value-change
 (fn [db [_ field value]]
   (-> db
      (assoc-in [:contact-form field :value] value)
      (assoc-in [:contact-form field :valid] ((field contact-form-validators) value)))))

(rf/reg-event-db :contact-form-submitted
 (fn [db _]
   (assoc db :contact-form-status :submitted)))

(rf/reg-event-db :reset-contact-form
 (fn [db _]
   (-> (reduce #(assoc-in %1 [:contact-form %2] {:value "" :valid nil}) db (keys (:contact-form db)))
       (assoc :contact-form-status nil))))

(rf/reg-fx :set-timeout
  (fn [[success-handler value]]
    (js/setTimeout #(rf/dispatch [success-handler]) value)))

(rf/reg-event-fx :contact-form-submit
  (fn [{:keys [db]} _]
    (if (some falsey? (map #(:valid %) (vals (:contact-form db))))
      {}
      {:db (assoc db :contact-form-status :submitting)
       :set-timeout [:contact-form-submitted 2000]})))
