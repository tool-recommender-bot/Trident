/*
 * Trident - A Multithreaded Server Alternative
 * Copyright 2014 The TridentSDK Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.tridentsdk.server.entity.living;

import net.tridentsdk.Coordinates;
import net.tridentsdk.entity.Entity;
import net.tridentsdk.entity.EntityProperties;
import net.tridentsdk.entity.living.Creeper;
import net.tridentsdk.entity.living.Player;
import net.tridentsdk.entity.projectile.Projectile;
import net.tridentsdk.event.entity.EntityDamageEvent;
import net.tridentsdk.server.entity.TridentLivingEntity;

import java.util.UUID;

public class TridentCreeper extends TridentLivingEntity implements Creeper {
    public TridentCreeper(UUID id, Coordinates spawnLocation) {
        super(id, spawnLocation);
    }

    @Override
    public boolean isElectric() {
        return false;
    }

    @Override
    public void setElectric(boolean powered) {

    }

    @Override
    public float getExplosionRadius() {
        return 0.0F;
    }

    @Override
    public void setExplosionRadius(float rad) {

    }

    @Override
    public void hide(Entity entity) {

    }

    @Override
    public void show(Entity entity) {

    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        return null;
    }

    @Override
    public Player hurtByPlayer() {
        return null;
    }

    @Override
    public boolean isNameVisible() {
        return false;
    }

    @Override
    public void applyProperties(EntityProperties properties) {

    }

    @Override
    public <T extends Projectile> T launchProjectile(EntityProperties properties) {
        return null;
    }
}