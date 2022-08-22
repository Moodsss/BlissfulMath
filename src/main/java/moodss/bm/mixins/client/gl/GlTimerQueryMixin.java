package moodss.bm.mixins.client.gl;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.GlTimer;
import org.lwjgl.opengl.ARBTimerQuery;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL32C;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GlTimer.Query.class)
public class GlTimerQueryMixin {

    @Shadow
    private long result;

    @Shadow
    @Final
    private int queryId;

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public boolean isResultAvailable() {
        RenderSystem.assertOnRenderThread();
        if(this.result == 0L) {
            try(MemoryStack stack = MemoryStack.stackPush()) {
                long statusPointer = stack.ncalloc(Long.BYTES, 0, Long.BYTES);
                ARBTimerQuery.nglGetQueryObjecti64v(this.queryId, GL15.GL_QUERY_RESULT, statusPointer);
                this.result = MemoryUtil.memGetInt(statusPointer);
            }
        }
        return this.result != GL15.GL_FALSE;
    }

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public long queryResult() {
        RenderSystem.assertOnRenderThread();
        if (this.result == 0L) {
            try(MemoryStack stack = MemoryStack.stackPush()) {
                long statusPointer = stack.ncalloc(Long.BYTES, 0, Long.BYTES);
                ARBTimerQuery.nglGetQueryObjecti64v(this.queryId, GL15.GL_QUERY_RESULT, statusPointer);
                this.result = MemoryUtil.memGetInt(statusPointer);
            }
            GL32C.glDeleteQueries(this.queryId);
        }

        return this.result;
    }
}
