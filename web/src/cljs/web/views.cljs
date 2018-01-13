(ns web.views
  (:require [re-frame.core :as re-frame]
            [web.subs :as subs]
            [web.constants :as constants]
            [clojure.string :as string]
            [web.events :as events]
            ))

;; helpers

(defn paragraph [para]
  [:p.flow-text.left-align para])


;; home

(defn home-panel []
  [:div.row
   [:div.col.s12.center-align [:img.responsive-img {:style {:height "25em"} :src constants/home-profile-picture}]]
   [:div.col.s12.center-align [:h3 (string/join " " [constants/title constants/full-name])]]
   [:div.col.s12.center-align [:h4 {:style {:margin-top 0}} constants/job-title]]
   [:div.col.s12.center-align [:h5 constants/academic-titles]]
   [:div.col.s12.center-align [:p.flow-text constants/summary]]])


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

(defn personal-contact-row [[icon content]]
  [:div.row
   [:div.col.s3 [:i.material-icons.medium icon]]
   [:div.col.s9 (map-indexed (fn [idx line] [:p.flow-text {:style (if (= idx 0) {:margin-top "0"})} line]) content)]])

(defn social-contact [[icon url]]
  [:div.col.s2
   [:a {:href url :style {:color "black"}}
   [:i.material-icons.medium {:class (str "ion-social-" (name icon))}]]])

(defn contact-methods []
  [:div
    (map personal-contact-row constants/personal-contacts)
    [:div.row
     (map social-contact constants/social-media)]])

(defn contact-panel []
  [:div.row
   [:div.col.s12.m5 {:style {:margin-top "2em"}} (contact-form)]
   [:div.col.s12.m2 {:style {:margin-top "2em"}}]
   [:div.col.s12.m5 {:style {:margin-top "2em"}} (contact-methods)]])


;; navigation

(defn nav-panel []
  [:div {:class "navbar-fixed"}
   [:nav.blue-grey.lighten-3e
    [:div {:class "nav-wrapper" :style {:text-align "center"}}
     [:ul {:class "center" :style {:display "inline-block"}}
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
