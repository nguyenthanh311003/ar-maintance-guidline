package com.capstone.ar_guideline.dtos.requests.Vuforia;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatasetRequest {
    private String name;
    private String targetSdk;
    private List<DatasetRequest.ModelData> models;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ModelData {
        private String name;
        private String cadDataUrl;
        private List<DatasetRequest.ViewData> views;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ViewData {
        private String name;
        private String layout;
        private DatasetRequest.GuideViewPosition guideViewPosition;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GuideViewPosition {
        private List<Float> translation;
        private List<Float> rotation;
    }
}