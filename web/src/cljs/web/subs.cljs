(ns web.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::contact-form-name
 (fn [db]
   (get-in db [:contact-form :name])))

(re-frame/reg-sub
 ::contact-form-email-address
 (fn [db]
   (get-in db [:contact-form :email-address])))

(re-frame/reg-sub
 ::contact-form-subject
 (fn [db]
   (get-in db [:contact-form :subject])))

(re-frame/reg-sub
 ::contact-form-message
 (fn [db]
   (get-in db [:contact-form :message])))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))
