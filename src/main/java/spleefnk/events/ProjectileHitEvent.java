package spleefnk.events;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockAir;
import cn.nukkit.block.BlockID;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.projectile.EntityArrow;
import cn.nukkit.entity.projectile.EntityProjectile;
import cn.nukkit.entity.projectile.EntitySnowball;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.math.Vector3;
import org.graalvm.compiler.nodes.gc.G1ArrayRangePostWriteBarrier;
import spleefnk.arena.Arena;
import spleefnk.arena.GameState;
import spleefnk.managers.ArenaManager;
import spleefnk.managers.GameManager;

public class ProjectileHitEvent implements Listener {

    private ArenaManager arenaManager;

    public ProjectileHitEvent(ArenaManager arenaManager) {
        this.arenaManager = arenaManager;
    }

    @EventHandler
    public void onProjHit(cn.nukkit.event.entity.ProjectileHitEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof EntityArrow || entity instanceof EntitySnowball) {
            EntityProjectile arrow = (EntityProjectile) entity;
            Entity entity1 = arrow.shootingEntity;
            if (entity1 instanceof Player) {
                Player player = (Player) entity1;
                for (Arena arena : arenaManager.getArenas()) {
                    if (arena.isPlaying(player)) {
                        if (arena.isBowSpleef()) {
                            if (arena.getGameState() == GameState.ACTIVE) {
                                if (arrow.getLevel().getBlockIdAt(arrow.getFloorX(), arrow.getFloorY() - 1, arrow.getFloorZ()) == BlockID.SNOW_BLOCK) {
                                    if (arrow instanceof EntityArrow){
                                        arrow.getLevel().setBlock(new Vector3(arrow.getX(), arrow.getLevelBlock().down().getY(), arrow.getZ()), Block.get(Block.AIR));
                                        arrow.getLevel().setBlock(new Vector3(arrow.getX(), arrow.getLevelBlock().down().getY(), arrow.getZ() - 1), Block.get(Block.AIR));
                                        arrow.getLevel().setBlock(new Vector3(arrow.getX() - 1, arrow.getLevelBlock().down().getY(), arrow.getZ()), Block.get(Block.AIR));
                                        arrow.getLevel().setBlock(new Vector3(arrow.getX() - 1, arrow.getLevelBlock().down().getY(), arrow.getZ() - 1), Block.get(Block.AIR));
                                    } else {
                                        arrow.getLevel().setBlock(new Vector3(arrow.getX(), arrow.getLevelBlock().down().getY(), arrow.getZ()), Block.get(Block.AIR));
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }
    }

}
