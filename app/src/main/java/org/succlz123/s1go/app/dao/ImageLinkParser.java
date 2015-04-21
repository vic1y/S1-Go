package org.succlz123.s1go.app.dao;

import android.content.Intent;
import android.os.Handler;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ImageSpan;
import android.view.MotionEvent;
import android.widget.TextView;
import org.succlz123.s1go.app.ui.activity.ImageViewActivity;

/**
 * Created by fashi on 2015/4/17.
 */
public class ImageLinkParser extends LinkMovementMethod {

    private static final ImageLinkParser instance = new ImageLinkParser();

    public static ImageLinkParser getInstance() {
        return instance;
    }

    private Handler handler = new Handler();
    private boolean find = false;
    private ImageSpan findSpan = null;

    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        Layout layout = ((TextView) widget).getLayout();

        if (layout == null) {
            return false;
        }

        int x = (int) event.getX();
        int y = (int) event.getY();

        int line = layout.getLineForVertical(y);
        int offset = layout.getOffsetForHorizontal(line, x);

        TextView tv = (TextView) widget;
        SpannableString value = SpannableString.valueOf(tv.getText());

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                ImageSpan[] imageSpans = value.getSpans(0, value.length(), ImageSpan.class);
                int findStart = 0;
                int findEnd = 0;

                for (ImageSpan imageSpan : imageSpans) {
                    int start = value.getSpanStart(imageSpan);
                    int end = value.getSpanEnd(imageSpan);
                    if (start <= offset && offset <= end) {
                        find = true;
                        findStart = start;
                        findEnd = end;
                        findSpan = imageSpan;
                        break;
                    }
                }

                return find;

            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                if (findSpan != null) {
                    String url = findSpan.getSource();
                    if (url.startsWith("static/image/smiley/")) {
                        return false;
                    }
                    Intent intent = new Intent(widget.getContext(), ImageViewActivity.class);
                    intent.putExtra("imageurl", url);

                    widget.getContext().startActivity(intent);

                }
                return true;
        }
        return false;
    }


//        Object[] spans = buffer.getSpans(off, off, spanClass);
//            if (spans.length != 0) {
//                if (action == MotionEvent.ACTION_DOWN) {
//                    Selection.setSelection(buffer, buffer.getSpanStart(spans[0]), buffer.getSpanEnd(spans[0]));
//                    MessageSpan obj = new MessageSpan();
//                    obj.setObj(spans);
//                    obj.setView(widget);
//                    Message message = handler.obtainMessage();
//                    message.obj = obj;
//                    message.sendToTarget();
//                }
//            }
//        }
}