(ns web.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub :contact-form-status
 (fn [db]
   (:contact-form-status db)))

(rf/reg-sub :contact-form-name
 (fn [db]
   (get-in db [:contact-form :name])))

(rf/reg-sub :contact-form-email-address
 (fn [db]
   (get-in db [:contact-form :email-address])))

(rf/reg-sub :contact-form-subject
 (fn [db]
   (get-in db [:contact-form :subject])))

(rf/reg-sub :contact-form-message
 (fn [db]
   (get-in db [:contact-form :message])))

(rf/reg-sub :active-panel
 (fn [db _]
   (:active-panel db)))
