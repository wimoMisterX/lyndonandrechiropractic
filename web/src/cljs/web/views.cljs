(ns web.views
  (:require [re-frame.core :as re-frame]
            [web.subs :as subs]
            [web.constants :as constants]
            [clojure.string :as string]
            [web.events :as events]
            ))

;; helpers

(defn paragraph [para]
  [:p.flow-text.center-align para])


;; home

(defn home-panel []
  (let []
    [:div
     [:div {:class "row"}
      [:div {:class "col s2"} [:img.responsive-img {:src constants/home-profile-picture}]]]
     [:div {:class "row"}
      [:div {:class "col s2"} (string/join " " [constants/title constants/full-name])]]
     [:div {:class "row"}
      [:div {:class "col s1"} constants/job-title]]
     [:div {:class "row"}
      [:div {:class "col s6"} constants/academic-titles]]
     [:div {:class "row"}
      [:div {:class "col s8"} constants/summary]]]))


;; about

(defn about-panel []
  [:div (map paragraph constants/about-content)])


;; experience

(defn experience-panel []
  [:div (map paragraph constants/experience-content)])


;; contact

(defn input-field [input-type field-name value label form-change-ev]
  [:div.input-field
    [:input.validate {:type input-type
                      :id field-name
                      :value value
                      :on-change #(re-frame/dispatch [form-change-ev (keyword field-name) (-> % .-target .-value)])}]
    [:label {:for field-name} label]])

(defn text-area [field-name value label form-change-ev]
  [:div.input-field
    [:textarea.materialize-textarea {:id field-name
                                     :value value
                                     :on-change #(re-frame/dispatch [form-change-ev (keyword field-name) (-> % .-target .-value)])}]
    [:label {:for field-name} label]])

(defn contact-form []
  (let [name (re-frame/subscribe [::subs/contact-form-name])
        email-address (re-frame/subscribe [::subs/contact-form-email-address])
        subject (re-frame/subscribe [::subs/contact-form-subject])
        message (re-frame/subscribe [::subs/contact-form-message])]
    [:div
     (input-field "text" "name" @name "Name" ::events/contact-form-value-change)
     (input-field "email" "email-address" @email-address "Email Address" ::events/contact-form-value-change)
     (input-field "text" "subject" @subject "Subject" ::events/contact-form-value-change)
     (text-area "message" @message "Message" ::events/contact-form-value-change)
     [:button.btn.waves-effect.waves-light
      "Submit"
      [:i.material-icons.right "send"]]]))

(defn social-contact [icon content icon-type]
  [:div.row
   [:div.col.s3 (if (= icon-type "material") [:i.material-icons icon] [:i.material-icons {:class icon}])]
   [:div.col.s9 content]])

(defn contact-panel []
  [:div.row
   [:div.col.s12.m5 (contact-form)]
   [:div.col.s12.m2]
   [:div.col.s12.m5
    (social-contact "phone" constants/phone-number "material")
    (social-contact "email" constants/email-address "material")
    (social-contact "my_location" constants/location-address "material")
    (social-contact "ion-social-facebook" constants/facebook "ion")
    (social-contact "ion-social-linkedin" constants/linkedin "ion")]])


;; navigation

(defn nav-panel []
  [:div {:class "navbar-fixed"}
   [:nav
    [:div {:class "nav-wrapper"}
     [:ul {:class "center hide-on-med-and-down"}
      [:li [:a {:href "#/"} "Home"]]
      [:li [:a {:href "#/about"} "About"]]
      [:li [:a {:href "#/experience"} "Experience"]]
      [:li [:a {:href "#/contact"} "Contact"]]]]]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :experience-panel [experience-panel]
    :contact-panel [contact-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div
     (nav-panel)
     [:div.container [show-panel @active-panel]]]))
