package me.simon.mixins;

import me.simon.commands.util.TextFormatter;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.*;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TranslatableText.class)
public abstract class TranslatableTextMixin  {


    protected TranslatableTextMixin(String key, Object[] args) {
        this.key = key;
        this.args = args;
    }



    @Inject(method = "<init>(Ljava/lang/String;[Ljava/lang/Object;)V", at = @At("RETURN"))
    public void TranslatableText(String key, Object[] args, CallbackInfo ci) {
            this.key = key;
            this.args = args;

            for(int i = 0; i < args.length; ++i) {
                Object object = args[i];
                if(object instanceof String){
                    this.args[i] = new LiteralText((String) object);
                }
                else if (object instanceof Text) {
                    Text text = ((Text)object).copy();
                    this.args[i] = text;
                    //text.getStyle().setParent(this.getStyle());
                } else if (object == null) {
                    this.args[i] = "null";
                }
                int x = 10;
            }
    }
    @Mutable
    @Final
    @Shadow
    private String key;
    @Mutable
    @Final
    @Shadow
    private Object[] args;
}
