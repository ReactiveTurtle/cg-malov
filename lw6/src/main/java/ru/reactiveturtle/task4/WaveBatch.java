package ru.reactiveturtle.task4;

import ru.reactiveturtle.sgl.Disposable;
import ru.reactiveturtle.sgl.RenderContext;
import ru.reactiveturtle.sgl.model.Model2d;
import ru.reactiveturtle.sgl.shader.ShaderMatrixData;
import ru.reactiveturtle.sgl.utils.MatrixUtils;

import java.util.Objects;

public class WaveBatch implements Disposable {
    private final WaveShaderProgram waveShader;
    private final RenderContext renderContext;

    public WaveBatch(RenderContext renderContext) {
        Objects.requireNonNull(renderContext);
        this.renderContext = renderContext;
        waveShader = new WaveShaderProgram();
    }

    public void begin() {
        waveShader.bind();
    }

    public void draw(Model2d model2d) {
        waveShader.load(new ShaderMatrixData(
                renderContext.getCamera().getProjectionMatrix(),
                renderContext.getCamera().getViewMatrix(),
                MatrixUtils.getModelMatrix(model2d)));

        model2d.draw(waveShader);
    }

    public void end() {
        waveShader.unbind();
    }

    @Override
    public void dispose() {
        waveShader.dispose();
    }
}
