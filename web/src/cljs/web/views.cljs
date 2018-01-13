(ns web.views
  (:require [re-frame.core :as re-frame]
            [web.subs :as subs]
            [web.constants :as constants]
            [clojure.string :as string]
            ))


;; home

(defn home-panel []
  (let []
    [:div
     [:div {:class "row"}
      [:div {:class "col s2"} [:img.profile-picture-home {:src constants/home-profile-picture}]]]
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
  [:div "This is the About Page."
   [:div [:a {:href "#/"} "go to Home Page"]]])


;; experience

(defn experience-panel []
  [:div "This is the experience Page."
   [:div [:a {:href "#/"} "go to Home Page"]]])


;; contact

(defn contact-panel []
  [:div "This is the contact Page."
   [:div [:a {:href "#/"} "go to Home Page"]]])


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
     [show-panel @active-panel]]))
