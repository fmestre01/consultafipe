package udacity.com.core.util.numberpicker;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import udacity.com.core.R;
import udacity.com.core.model.Ano;

public class NumberPicker extends LinearLayout {

    private float mItemBigHeight;
    private float mItemSmallHeight;
    private int mAllVerticalScroll;
    private final RecyclerView mRecyclerView;
    private ValuePickerAdapter mValuePickerAdapter;
    private float mTextSize;
    private float mTextSizeSelected;
    private int mTextColor;
    private int mTextColorSelected;
    private boolean mAnimateTextSize, mTextFadeColor;
    private OnValueChangeListener mOnValueChangeListener;
    private List<Ano> values;

    public NumberPicker(Context context, List<Ano> values) {
        this(context, null, values);
    }

    public NumberPicker(Context context, AttributeSet attrs, List<Ano> values) {
        this(context, attrs, 0, values);
    }

    public NumberPicker(Context context, AttributeSet attrs, int defStyleAttr, List<Ano> values) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.np_ValuePicker, defStyleAttr, 0);

        Resources resources = context.getResources();

        mTextColor = a.getColor(R.styleable.np_ValuePicker_np_text_color, ContextCompat.getColor(context, R.color.np_text_color));
        mTextSize = a.getDimension(R.styleable.np_ValuePicker_np_text_size, resources.getDimension(R.dimen.np_text_size));
        mTextColorSelected = a.getColor(R.styleable.np_ValuePicker_np_text_color, ContextCompat.getColor(context, R.color.np_text_color_selected));
        mTextSizeSelected = a.getDimension(R.styleable.np_ValuePicker_np_text_size, resources.getDimension(R.dimen.np_text_size_selected));
        mTextFadeColor = a.getBoolean(R.styleable.np_ValuePicker_np_fade_text_color, resources.getBoolean(R.bool.np_def_fade_color));
        mAnimateTextSize = a.getBoolean(R.styleable.np_ValuePicker_np_animate_text_size, resources.getBoolean(R.bool.np_def_animate_text_size));

        a.recycle();

        setMinimumWidth(context.getResources().getDimensionPixelSize(R.dimen.np_min_width));

        mItemSmallHeight = getTextViewHeight(context, false, mTextSize, mTextSizeSelected);
        mItemBigHeight = getTextViewHeight(context, true, mTextSize, mTextSizeSelected);

        mRecyclerView = new RecyclerView(context);
        int listHeight = (int) (mItemSmallHeight * 2 + mItemBigHeight);
        mRecyclerView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, listHeight));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mAllVerticalScroll = 0;

        final LinearLayoutManager dateLayoutManager = new LinearLayoutManager(context);
        dateLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(dateLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                synchronized (this) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        calculatePositionAndScroll();
                    } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        if (mValuePickerAdapter != null) {
                            mValuePickerAdapter.setSelectedIndex(ValuePickerAdapter.POSITION_NONE);
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mAllVerticalScroll += dy;
            }
        });
        mValuePickerAdapter = new ValuePickerAdapter(context, values);
        mRecyclerView.setAdapter(mValuePickerAdapter);
        mValuePickerAdapter.setSelectedIndex(0);
        addView(mRecyclerView);
    }

    public OnValueChangeListener getOnValueChangeListener() {
        return mOnValueChangeListener;
    }

    public void setOnValueChangeListener(OnValueChangeListener mOnValueChangeListener) {
        this.mOnValueChangeListener = mOnValueChangeListener;
    }

    public void updateValues(List<Ano> values) {
        this.mValuePickerAdapter = new ValuePickerAdapter(getContext(), values);
        this.mRecyclerView.setAdapter(this.mValuePickerAdapter);
        this.mValuePickerAdapter.setSelectedIndex(0);
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
    }

    public float getTextSizeSelected() {
        return mTextSizeSelected;
    }

    public void setTextSizeSelected(float mTextSizeSelected) {
        this.mTextSizeSelected = mTextSizeSelected;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getTextColorSelected() {
        return mTextColorSelected;
    }

    public void setTextColorSelected(int mTextColorSelected) {
        this.mTextColorSelected = mTextColorSelected;
    }

    public String getSelectedValue() {
        return mValuePickerAdapter.getSelectedNumber();
    }

    private void calculatePositionAndScroll() {
        int expectedPosition = Math.round(mAllVerticalScroll / mItemSmallHeight);
        if (expectedPosition == -1) {
            expectedPosition = 0;
        } else if (expectedPosition >= mRecyclerView.getAdapter().getItemCount() - 2) {
            expectedPosition = mRecyclerView.getAdapter().getItemCount() - 2;
            mAllVerticalScroll = Math.round(expectedPosition * mItemSmallHeight);
        }
        scrollListToPosition(expectedPosition);
    }

    private static int dp2px(Context context, int dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }

    private void scrollListToPosition(int expectedPosition) {
        float targetScrollPosDate = expectedPosition * mItemSmallHeight;
        final float missingPxDate = targetScrollPosDate - mAllVerticalScroll;
        if (missingPxDate != 0) {
            mRecyclerView.smoothScrollBy(0, (int) missingPxDate);
        }
        mValuePickerAdapter.setSelectedAbsoluteIndex(Math.round(mAllVerticalScroll / mItemSmallHeight) + 1);
    }

    @NonNull
    private static TextView getTextView(Context context, boolean isBig, float textSize, float textSizeSelected) {
        TextView number = new TextView(context);
        number.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        number.setGravity(Gravity.CENTER_HORIZONTAL);
        if (isBig) {
            number.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSizeSelected);
        } else {
            number.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        return number;
    }

    @NonNull
    private TextView getTextView(Context context, float textSize, float textSizeSelected) {
        return getTextView(context, false, textSize, textSizeSelected);
    }

    public static int getTextViewHeight(Context context, boolean isBig, float textSize, float textSizeSelected) {
        TextView textView = getTextView(context, isBig, textSize, textSizeSelected);
        textView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        return textView.getMeasuredHeight();
    }

    private class ValuePickerAdapter extends RecyclerView.Adapter<ValuePickerAdapter.Holder> {
        private static final int VIEW_TYPE_PADDING = 0;
        private static final int VIEW_TYPE_ITEM = 1;
        private Context mContext;
        static final int POSITION_NONE = -1;

        private int selectedItemIndex = POSITION_NONE;
        private List<Ano> values = new ArrayList<>();

        ValuePickerAdapter(Context context, List<Ano> values) {
            this.mContext = context;
            int size = values.size();
            for (int i = 0; i < size; i++) {
                values.add(values.get(i));
            }
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == VIEW_TYPE_ITEM) {
                TextView number = getTextView(mContext, mTextSize, mTextSizeSelected);
                return new ItemHolder(number);
            } else {
                View paddingView = new View(mContext);
                RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(dp2px(mContext, 1), (int) mItemSmallHeight);
                paddingView.setLayoutParams(layoutParams);
                return new PaddingHolder(paddingView);
            }
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

            if (holder instanceof PaddingHolder) {
                PaddingHolder paddingHolder = (PaddingHolder) holder;
                ViewGroup.LayoutParams params = paddingHolder.itemView.getLayoutParams();
                if (position != 0) {
                    params.height = (int) (mItemSmallHeight + mItemBigHeight - mItemSmallHeight);
                } else {
                    params.height = (int) mItemSmallHeight;
                }
            }
            if (holder instanceof ItemHolder) {
                final ItemHolder itemHolder = (ItemHolder) holder;
                int adjustedPosition = position - 1; // Adjusted position removes the 1st padding

                itemHolder.number.setText(values.get(adjustedPosition).getId()); //minus padding view

                if (adjustedPosition == selectedItemIndex) {
                    if (mTextFadeColor) {
                        final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), itemHolder.number.getCurrentTextColor(), ContextCompat.getColor(mContext, R.color.np_text_color_selected));
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                itemHolder.number.setTextColor((Integer) animator.getAnimatedValue());
                            }

                        });
                        colorAnimation.start();
                    }
                    if (mAnimateTextSize) {
                        ValueAnimator textSizeAnimation = ValueAnimator.ofObject(new FloatEvaluator(), mTextSize, mTextSizeSelected);
                        textSizeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                itemHolder.number.setTextSize(TypedValue.COMPLEX_UNIT_PX, (Float) animator.getAnimatedValue());
                            }
                        });
                        textSizeAnimation.start();
                    }

                } else {
                    itemHolder.number.setTextColor(ContextCompat.getColor(mContext, R.color.np_text_color));
                    itemHolder.number.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == getItemCount() - 1) {
                return VIEW_TYPE_PADDING;
            }
            return VIEW_TYPE_ITEM;
        }

        void setSelectedAbsoluteIndex(int absoluteIndex) {
            if (getItemViewType(absoluteIndex) == VIEW_TYPE_PADDING) {
                setSelectedIndex(POSITION_NONE);
                return;
            }
            setSelectedIndex(absoluteIndex - 1); // Adjust to position index
        }

        void setSelectedIndex(int selectedIndex) {
            if (selectedIndex != this.selectedItemIndex) {
                if (mOnValueChangeListener != null && selectedIndex != POSITION_NONE) {
                    mOnValueChangeListener.onValueChanged(values.get(selectedIndex).getId());
                }
                this.selectedItemIndex = selectedIndex;
                notifyDataSetChanged();
            }

        }

        String getSelectedNumber() {
            if (selectedItemIndex == POSITION_NONE) {
                return "";
            }
            return values.get(selectedItemIndex).getId();
        }

        @Override
        public int getItemCount() {
            return values.size() + 2; // calculate number of items plus 2 padding
        }

        private class PaddingHolder extends Holder {

            private PaddingHolder(View itemView) {
                super(itemView);

            }
        }

        private class ItemHolder extends Holder {
            private TextView number;

            private ItemHolder(View itemView) {
                super(itemView);
                number = (TextView) itemView;
            }
        }

        class Holder extends RecyclerView.ViewHolder {
            private Holder(View itemView) {
                super(itemView);
            }
        }
    }
}
