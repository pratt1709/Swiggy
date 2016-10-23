package com.prateek.swiggy.domain;

import com.prateek.swiggy.rest.Request.GroupChoice;
import com.prateek.swiggy.rest.Request.VariantGroup;
import com.prateek.swiggy.rest.Request.Variation;

import java.util.ArrayList;

/**
 * Created by prateek.kesarwani on 22/10/16.
 */

// Multiple ways to handle this, but for limited time, this could work
public class ExclusionRules {

    public VariantGroup getVariationAfterExclusion(ArrayList<GroupChoice> selectedValues,
                                                   VariantGroup originalVariant,
                                                   ArrayList<ArrayList<GroupChoice>> excludeListArr) {

        if (selectedValues != null && selectedValues.size() > 0) {

            VariantGroup resultVariant = (VariantGroup) originalVariant.clone();

            for (Variation variation : resultVariant.getVariations()) {
                variation.setEnabled(true);
            }

            ArrayList<GroupChoice> excludedGroup = new ArrayList<>();
            for (ArrayList<GroupChoice> groups : excludeListArr) {
                for (GroupChoice selectedChoice : selectedValues) {
                    if (groups.contains(selectedChoice)) {
                        excludedGroup.addAll(groups);
                        excludedGroup.remove(selectedChoice);
                    }
                }
            }

            if (excludedGroup != null) {
                for (GroupChoice groupChoice : excludedGroup) {
                    if (resultVariant.getGroupId().equalsIgnoreCase(groupChoice.getGroupId())) {
                        for (Variation variation : resultVariant.getVariations()) {
                            if (groupChoice.getVariationId().equalsIgnoreCase(variation.getId())) {
                                variation.setEnabled(false);
                            }
                        }
                    }
                }
            }

            return resultVariant;
        }

        return originalVariant;

    }
}
