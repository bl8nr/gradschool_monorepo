/**
 * reddit profile interface/typing
 */
export interface Profile {
    accountInfo: AccountInfo;
    authorization?: any;
    members: string[];
    createdAt: Date;
    ownerId: String;
    __v: Number;
    _id: String;
}

/**
 * account info interface/typing
 */
export interface AccountInfo {
    comment_karma: number;
    created: number;
    created_utc: number;
    features: any;
    gold_creddits: number;
    gold_expiration?: any;
    has_mail: boolean;
    has_mod_mail: boolean;
    has_subscribed: boolean;
    has_verified_email: boolean;
    has_visited_new_profile: boolean;
    hide_from_robots: boolean;
    icon_img: string;
    id: string;
    in_beta: boolean;
    in_redesign_beta: boolean;
    inbox_count: string;
    is_employee: boolean;
    is_gold: boolean;
    is_mod: boolean;
    is_sponsor: boolean;
    is_suspended: boolean;
    link_karma: number;
    name: string;
    new_modmail_exists?: any;
    oauth_client_id: string;
    over_18: boolean;
    pref_clickgadget: number;
    pref_geopopular: string;
    pref_no_profanity: boolean;
    pref_show_snoovatar: boolean;
    pref_show_trending: boolean;
    pref_top_karma_subreddits: boolean;
    subreddit: any;
    suspension_expiration_utc?: any;
    verified: boolean;
}
