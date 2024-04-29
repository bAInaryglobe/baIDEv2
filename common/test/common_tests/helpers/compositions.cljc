;; This Source Code Form is subject to the terms of the Mozilla Public
;; License, v. 2.0. If a copy of the MPL was not distributed with this
;; file, You can obtain one at http://mozilla.org/MPL/2.0/.
;;
;; Copyright (c) KALEIDOS INC

(ns common-tests.helpers.compositions
  (:require
   [common-tests.helpers.files :as thf]
   [common-tests.helpers.ids-map :as thi]))

(defn add-rect
  [file rect-label & {:keys [] :as params}]
  (thf/add-sample-shape file rect-label
                        (merge {:type :rect
                                :name "Rect1"}
                               params)))

(defn add-frame
  [file frame-label & {:keys [] :as params}]
  (thf/add-sample-shape file frame-label
                        (merge {:type :frame
                                :name "Frame1"}
                               params)))

(defn add-frame-with-child
  [file frame-label child-label & {:keys [frame-params child-params]}]
  (-> file
      (add-frame frame-label frame-params)
      (thf/add-sample-shape child-label
                            (merge {:type :rect
                                    :name "Rect1"
                                    :parent-label frame-label}
                                   child-params))))

(defn add-simple-component
  [file component-label root-label child-label
   & {:keys [component-params root-params child-params]}]
  (-> file
      (add-frame-with-child root-label child-label :frame-params root-params :child-params child-params)
      (thf/make-component component-label root-label component-params)))

(defn add-simple-component-with-copy
  [file component-label main-root-label main-child-label copy-root-label
   & {:keys [component-params main-root-params main-child-params copy-root-params]}]
  (-> file
      (add-simple-component component-label
                            main-root-label
                            main-child-label
                            :component-params component-params
                            :root-params main-root-params
                            :child-params main-child-params)
      (thf/instantiate-component component-label copy-root-label copy-root-params)))
