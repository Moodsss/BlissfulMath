package moodss.bm.mixins.client.gl;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.shaders.ProgramManager;
import com.mojang.blaze3d.shaders.Shader;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ProgramManager.class)
public class GlProgramManagerMixin {

    @Shadow
    @Final
    private static Logger LOGGER;

    /**
     * @author Mo0dss
     * @reason Do not create buffers
     */
    @Overwrite
    public static void linkShader(Shader shader) {
        RenderSystem.assertOnRenderThread();
        shader.attachToProgram();
        GlStateManager.glLinkProgram(shader.getId());

        try(MemoryStack stack = MemoryStack.stackPush()) {
            long statusPointer = stack.ncalloc(Integer.BYTES, 0, Integer.BYTES);
            GL20.nglGetProgramiv(shader.getId(), GlConst.GL_LINK_STATUS, statusPointer);

            if(MemoryUtil.memGetInt(statusPointer) == GL11.GL_FALSE) {
                LOGGER.warn(
                        "Error encountered when linking program containing VS {} and FS {}. Log output:", shader.getVertexProgram().getName(), shader.getFragmentProgram().getName()
                );
                LOGGER.warn(GlStateManager.glGetProgramInfoLog(shader.getId(), 32768));
            }
        }
    }
}
