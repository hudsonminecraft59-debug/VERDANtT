package com.verdant.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import java.util.List;

public final class VerdantClickGui extends Screen {
    private static final int BG = 0xE8071511, PANEL = 0xE91A3027, PANEL2 = 0xE91E392E, MINT = 0xFF54E6B0, TEXT = 0xFFE6F5ED, MUTED = 0xFF8FA99B, LINE = 0xFF315345;
    private int left, top, panelW = 164, panelH = 220;
    private final List<Integer> palette = List.of(0x54E6B0,0x6EC8FF,0xB78CFF,0xFFB86B,0xFF6E8A,0xF1F5F2,0xF0D96B);
    public VerdantClickGui() { super(Text.literal("Verdant ClickGUI")); }
    @Override protected void init() { left = (width - 524) / 2; top = (height - 282) / 2; }
    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0,0,width,height,BG);
        // soft ambient circles
        ctx.fill(left-40, top+20, left+150, top+210, 0x102EBA83);
        ctx.fill(left+340, top-30, left+560, top+190, 0x0F4ABF8C);
        text(ctx,"VERDANT",left,top-20, TEXT, 1.0f); text(ctx,"CLIENT  /  BLOCK VISUALS",left,top-7,MUTED,0.65f);
        pill(ctx,left+430,top-21,72,16,"1.21.11",PANEL2,MINT); pill(ctx,left+507,top-21,18,16,"×",PANEL2,TEXT);
        panel(ctx,left,top+12,"Visuals","ESP / WORLD",0);
        panel(ctx,left+180,top+12,"Combat","MODULES",1);
        panel(ctx,left+360,top+12,"Utility","CLIENT",2);
        text(ctx,"RIGHT SHIFT  ·  OPEN / CLOSE",left,top+250,MUTED,0.65f);
        text(ctx,"SETTINGS SAVED LOCALLY",left+355,top+250,MUTED,0.65f);
        super.render(ctx,mouseX,mouseY,delta);
    }
    private void panel(DrawContext c,int x,int y,String title,String sub,int which) {
        c.fill(x,y,x+panelW,y+panelH, PANEL); c.fill(x,y,x+panelW,y+1,LINE); c.fill(x,y,x+panelW,y+31,0xA51C382C);
        text(c,"◈",x+10,y+9,MINT,0.9f); text(c,title,x+25,y+7,TEXT,0.8f); text(c,sub,x+25,y+18,MUTED,0.55f);
        if(which==0) {
            toggle(c,x,y+43,"Storage ESP",VerdantConfig.storageEsp,"CHESTS · HOPPERS · SHULKERS");
            row(c,x,y+83,"Outline color",colorName(VerdantConfig.storageColor),true);
            slider(c,x,y+111,"Outline width",VerdantConfig.outlineWidth,1,5);
            toggle(c,x,y+142,"Fill overlay",VerdantConfig.storageFill,null);
            slider(c,x,y+172,"Fill opacity",VerdantConfig.storageFillAlpha,0,100);
            row(c,x,y+202,"Visible range",VerdantConfig.range+" blocks",true);
        } else if(which==1) {
            toggle(c,x,y+43,"Spawner Finder",VerdantConfig.spawnerFinder,"DUNGEON / MINESHAFT SPAWNERS");
            row(c,x,y+83,"Outline color",colorName(VerdantConfig.spawnerColor),true);
            slider(c,x,y+111,"Outline width",VerdantConfig.outlineWidth,1,5);
            toggle(c,x,y+142,"Fill overlay",VerdantConfig.spawnerFill,null);
            slider(c,x,y+172,"Fill opacity",VerdantConfig.spawnerFillAlpha,0,100);
            row(c,x,y+202,"Visible range",VerdantConfig.range+" blocks",true);
        } else {
            row(c,x,y+43,"Search modules","⌕",false);
            row(c,x,y+73,"Theme","Verdant",false);
            row(c,x,y+103,"Accent","Mint",false);
            toggle(c,x,y+133,"Show module hints",true,null);
            row(c,x,y+163,"Config file","verdant.properties",false);
            text(c,"Independent ESP colors",x+11,y+199,MUTED,0.6f);
        }
    }
    private void toggle(DrawContext c,int x,int y,String label,boolean enabled,String hint) {
        text(c,label,x+10,y,TEXT,0.7f); c.fill(x+panelW-29,y+1,x+panelW-10,y+10,enabled?MINT:0xFF496157); c.fill(x+panelW-(enabled?20:28),y+2,x+panelW-(enabled?12:20),y+9,0xFFF1FFF8);
        if(hint!=null) text(c,hint,x+10,y+13,MUTED,0.48f);
    }
    private void row(DrawContext c,int x,int y,String label,String value,boolean click) { text(c,label,x+10,y,TEXT,0.65f); text(c,value,x+panelW-10-(value.length()*4),y,click?MINT:MUTED,0.6f); }
    private void slider(DrawContext c,int x,int y,String label,int value,int min,int max) { text(c,label,x+10,y,TEXT,0.62f); text(c,""+value,x+panelW-22,y,MINT,0.62f); int sx=x+10, ex=x+panelW-10, yy=y+15; c.fill(sx,yy,ex,yy+2,0xFF3C594C); int knob=sx+(ex-sx)*(value-min)/(max-min); c.fill(sx,yy,knob,yy+2,MINT); c.fill(knob-3,yy-2,knob+3,yy+4,TEXT); }
    private void pill(DrawContext c,int x,int y,int w,int h,String label,int bg,int fg){c.fill(x,y,x+w,y+h,bg);text(c,label,x+5,y+4,fg,0.55f);}
    private void text(DrawContext c,String s,int x,int y,int color,double scale){ c.getMatrices().push(); c.getMatrices().translate(x,y,0); c.getMatrices().scale((float)scale,(float)scale,1); c.drawText(this.textRenderer,s,0,0,color,false); c.getMatrices().pop(); }
    private String colorName(int rgb) { return String.format("#%06X",rgb); }
    @Override public boolean mouseClicked(double mx,double my,int button) {
        int x=left,y=top+12;
        if(button==0) {
            if(hit(mx,my,x,y+43)) VerdantConfig.storageEsp=!VerdantConfig.storageEsp;
            else if(hit(mx,my,x,y+83)) VerdantConfig.storageColor=next(VerdantConfig.storageColor);
            else if(hit(mx,my,x,y+111)) VerdantConfig.outlineWidth=cycle(VerdantConfig.outlineWidth,1,5);
            else if(hit(mx,my,x,y+142)) VerdantConfig.storageFill=!VerdantConfig.storageFill;
            else if(hit(mx,my,x,y+172)) VerdantConfig.storageFillAlpha=cycle(VerdantConfig.storageFillAlpha,0,100,10);
            else if(hit(mx,my,x,y+202)) VerdantConfig.range=cycle(VerdantConfig.range,8,128,8);
            int sx=left+180;
            if(hit(mx,my,sx,y+43)) VerdantConfig.spawnerFinder=!VerdantConfig.spawnerFinder;
            else if(hit(mx,my,sx,y+83)) VerdantConfig.spawnerColor=next(VerdantConfig.spawnerColor);
            else if(hit(mx,my,sx,y+111)) VerdantConfig.outlineWidth=cycle(VerdantConfig.outlineWidth,1,5);
            else if(hit(mx,my,sx,y+142)) VerdantConfig.spawnerFill=!VerdantConfig.spawnerFill;
            else if(hit(mx,my,sx,y+172)) VerdantConfig.spawnerFillAlpha=cycle(VerdantConfig.spawnerFillAlpha,0,100,10);
            else if(hit(mx,my,sx,y+202)) VerdantConfig.range=cycle(VerdantConfig.range,8,128,8);
            VerdantConfig.save();
        }
        return super.mouseClicked(mx,my,button);
    }
    private boolean hit(double mx,double my,int x,int y){return mx>=x&&mx<=x+panelW&&my>=y&&my<=y+24;}
    private int next(int rgb){int i=palette.indexOf(rgb);return palette.get((i+1+palette.size())%palette.size());}
    private int cycle(int v,int min,int max){return v>=max?min:v+1;}
    private int cycle(int v,int min,int max,int step){return v+step>max?min:v+step;}
    @Override public boolean shouldPause(){return false;}
}
