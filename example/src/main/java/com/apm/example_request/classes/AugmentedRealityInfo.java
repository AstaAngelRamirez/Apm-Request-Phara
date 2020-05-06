package com.apm.example_request.classes;

/**
 * Created by Ing. Oscar G. Medina Cruz on 30/05/18.
 */
public class AugmentedRealityInfo {
    private String model_name;
    private String image_url;
    private Model models;

    public String getModelName() {
        return model_name;
    }

    public String getImageURL() {
        return image_url;
    }

    public Model getModel() {
        return models;
    }

    private class Model {
        private IOS ios;
        private Android android;

        public IOS getIOS() {
            return ios;
        }

        public Android getAndroid() {
            return android;
        }
    }

    private class IOS {
        private boolean is_animated;
        private Urls urls;

        public boolean isAnimated() {
            return is_animated;
        }

        public Urls getUrls() {
            return urls;
        }
    }

    private class Android {
        private boolean is_animated;
        private Urls urls;

        public boolean isAnimated() {
            return is_animated;
        }

        public Urls getUrls() {
            return urls;
        }
    }

    private class Urls {
        private String model;
        private String controller;

        public String getModel() {
            return model;
        }

        public String getController() {
            return controller;
        }
    }
}
