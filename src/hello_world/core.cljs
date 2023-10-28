(ns hello-world.core
  (:require ["express" :as express]))

(def app (express))
(def port 3000)

(defn- send-result [res data]
  (. res send (:body data)))

(defn handle-root-2 [req]
  (. js/Promise resolve
    {:body "Hello, world inside a promise!"}))

(defn- register-get [app handler]
  (. app get "/" (fn [req res]
                   (let [response-promise-maybe (handler req)]
                     (if (.-then response-promise-maybe)
                       (.then response-promise-maybe
                         (fn [response] (send-result res response)))
                       (send-result res response-promise-maybe))))))

(defn register-handlers []
  (register-get app handle-root-2)
  (. app listen port (fn []
                       (println "Example app listenning on port" port))))

(defn ^:dev/after-load main []
  (def server (register-handlers)))

(defn ^:dev/before-load stop-server []
  (. server close)
  (println "Stopped server"))
