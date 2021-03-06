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
package vnreal.network.substrate;

import vnreal.network.Link;
import vnreal.resources.AbstractResource;
import vnreal.resources.BandwidthResource;

/**
 * A substrate network link class.
 *
 * @author Michael Duelli
 * @author Vlad Singeorzan
 */
public final class SubstrateLink extends Link<AbstractResource> {

    private BandwidthResource bandwidthResource;

    public SubstrateLink(int id) {
        super(id);
    }

    public SubstrateLink() {
        super();
    }

    public BandwidthResource getBandwidthResource() {
        return bandwidthResource;
    }

    @Override
    public boolean add(AbstractResource abstractResource) {
        if (super.add(abstractResource)) {
            if (BandwidthResource.class.isAssignableFrom(abstractResource.getClass())) {
                bandwidthResource = (BandwidthResource) abstractResource;
            }

            return true;
        }

        return false;
    }

    @Override
    public SubstrateLink clone() {
        SubstrateLink clone = new SubstrateLink();

        for (AbstractResource r : this) {
            clone.add(r.getCopy(this));
        }

        return clone;
    }

    @Override
    public String toString() {
        return "SubstrateLink(" + getId() + ")";
    }

    @Override
    public String toStringShort() {
        return "SL(" + getId() + ")";
    }
}
