/*
 *     Trident - A Multithreaded Server Alternative
 *     Copyright (C) 2014, The TridentSDK Team
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.tridentsdk.entity;

import com.google.common.util.concurrent.AtomicDouble;
import net.tridentsdk.api.Location;
import net.tridentsdk.api.entity.LivingEntity;
import net.tridentsdk.api.util.Vector;

import java.util.UUID;

/**
 * An entity that has health
 *
 * @author The TridentSDK Team
 */
public abstract class TridentLivingEntity extends TridentEntity implements LivingEntity {
    /**
     * Whether the entity is dead
     */
    protected final boolean dead;
    /**
     * Whether the entity can pick up items
     */
    protected final boolean canPickup = true;
    /**
     * The entity health
     */
    protected final AtomicDouble health = new AtomicDouble(0.0);
    /**
     * The maximum available health
     */
    protected double maxHealth;
    /**
     * Ticks since the entity was last set on fire and when the fire burned out
     */
    protected volatile AtomicDouble fireTicks = new AtomicDouble(0.0);
    /**
     * The amount of air the entity has, depleted when in water
     */
    protected final AtomicDouble airTicks = new AtomicDouble(0.0);

    /**
     * Inherits from {@link net.tridentsdk.entity.TridentEntity}
     *
     * <p>The entity is immediately set "non-dead" after {@code super} call</p>
     */
    public TridentLivingEntity(UUID id, Location spawnLocation) {
        super(id, spawnLocation);

        this.dead = false;
    }

    @Override
    public double getHealth() {
        return this.health.get();
    }

    @Override
    public void setHealth(double health) {
        this.health.set(health);
    }

    @Override
    public double getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public void setMaxHealth(double maxHealth) {
        this.maxHealth = maxHealth;
    }

    @Override
    public Location getEyeLocation() {
        return this.getLocation().getRelative(new Vector(0.0d, 1.0d, 0.0d));
    }

    @Override
    public long getRemainingAir() {
        return (long) this.airTicks.get();
    }

    @Override
    public void setRemainingAir(long ticks) {
        this.airTicks.set((double) ticks);
    }

    @Override
    public boolean canPickupItems() {
        return this.canPickup;
    }

    @Override
    public boolean isDead() {
        return this.dead;
    }
}
