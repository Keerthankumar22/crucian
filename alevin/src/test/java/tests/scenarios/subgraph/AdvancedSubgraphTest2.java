/* ***** BEGIN LICENSE BLOCK *****
 * Copyright (C) 2010-2011, The VNREAL Project Team.
 * 
 * This work has been funded by the European FP7
 * Network of Excellence "Euro-NF" (grant agreement no. 216366)
 * through the Specific Joint Developments and Experiments Project
 * "Virtual Network Resource Embedding Algorithms" (VNREAL). 
 *
 * The VNREAL Project Team consists of members from:
 * - University of Wuerzburg, Germany
 * - Universitat Politecnica de Catalunya, Spain
 * - University of Passau, Germany
 * See the file AUTHORS for details and contact information.
 * 
 * This file is part of ALEVIN (ALgorithms for Embedding VIrtual Networks).
 *
 * ALEVIN is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License Version 3 or later
 * (the "GPL"), or the GNU Lesser General Public License Version 3 or later
 * (the "LGPL") as published by the Free Software Foundation.
 *
 * ALEVIN is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * or the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License and
 * GNU Lesser General Public License along with ALEVIN; see the file
 * COPYING. If not, see <http://www.gnu.org/licenses/>.
 *
 * ***** END LICENSE BLOCK ***** */
package tests.scenarios.subgraph;

import mulavito.algorithms.IAlgorithm;
import org.junit.Ignore;
import org.junit.Test;
import tests.io.PrintWriterDataReceiver;
import tests.scenarios.jisa.AbstractLoadScenarioTest;
import tests.scenarios.jisa.AbstractNoLoadScenarioTest;
import vnreal.ToolKit;
import vnreal.algorithms.isomorphism.AdvancedSubgraphIsomorphismAlgorithm2;
import vnreal.algorithms.isomorphism.SubgraphIsomorphismStackAlgorithm;
import vnreal.algorithms.utils.SubgraphBasicVN.Utils;
import vnreal.evaluations.metrics.EvaluationMetric;
import vnreal.network.NetworkStack;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Ignore
public final class AdvancedSubgraphTest2 extends AbstractNoLoadScenarioTest {

    private final PrintWriterDataReceiver writer;

    public AdvancedSubgraphTest2(ScenarioData data) throws IOException {
        super(data);

        writer = new PrintWriterDataReceiver(
                new PrintWriter(new FileWriter(
                        getName() + "_out.txt", true),
                        true));

        numRunsPerScenario = 1;
    }

    @Override
    public void generateCPUResourcesAndDemands(NetworkStack stack,
                                               int minResourceCPU, int maxResourceCPU,
                                               int minDemandCPU, int maxDemandCPU) {
        AbstractNoLoadScenarioTest.generateRandomCPUResourcesAndDemands(
                stack,
                minResourceCPU, maxResourceCPU,
                minDemandCPU, maxDemandCPU);
    }

    @Override
    public void generateBandwidthResourceAndDemands(NetworkStack stack,
                                                    int minResourceBandwidth, int maxResourceBandwidth,
                                                    int minDemandBandwidth, int maxDemandBandwidth) {
        AbstractNoLoadScenarioTest.generateRandomBandwidthResourceAndDemands(
                stack,
                minResourceBandwidth, maxResourceBandwidth,
                minDemandBandwidth, maxDemandBandwidth);
    }

    @Override
    protected void generateAdditionalConstraints() {
        Utils.generateEnergyDemands(ToolKit.getScenario().getNetworkStack());
        AbstractNoLoadScenarioTest.generateRandomStaticEnergyConsumptionResources();
    }

    @Override
    @Test
    public void runScenario() {
        super.runScenario();
        writer.finish();
    }

    @Override
    protected void evaluate(String scenarioSuffix, long elapsedTime) {

        NetworkStack stack = ToolKit.getScenario().getNetworkStack();
//		AvailableResourcesPathSplitting.sortByRevenues(stack);

        for (EvaluationMetric metric : AbstractLoadScenarioTest.getDefaultMetrics()) {
            metric.setStack(stack);
            double y = metric.getValue();

            writer.receive(
                    stack,
                    metric, scenario_suffix, y, elapsedTime);
        }

    }

    @Override
    protected void runAlgorithm() {
        IAlgorithm algo =
                new SubgraphIsomorphismStackAlgorithm(
                        ToolKit.getScenario().getNetworkStack(),
                        new AdvancedSubgraphIsomorphismAlgorithm2(true));

        algo.performEvaluation();
    }
}
